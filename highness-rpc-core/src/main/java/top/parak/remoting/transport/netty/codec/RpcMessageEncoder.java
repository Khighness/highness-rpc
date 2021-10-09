package top.parak.remoting.transport.netty.codec;

import top.parak.compress.Compress;
import top.parak.enums.CompressTypeEnum;
import top.parak.enums.SerializationTypeEnum;
import top.parak.extension.ExtensionLoader;
import top.parak.remoting.constants.RpcConstants;
import top.parak.remoting.dto.RpcMessage;
import top.parak.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * custom protocol decoder
 * <pre>
 *   0     4     5     9     10    11    12    16
 *   +-----+-----+-----+-----+-----+-----+-----+
 *   |  MC |  V  | LEN |  T  |  Z  |  C  | ID  |
 *   +-----+-----+-----+-----+-----+-----+-----+
 *   |                  BODY                   |
 *   +-----+-----+-----+-----+-----+-----+-----+
 * </pre>
 * <ul>
 *     <li>MC (magic code, 魔法数): 4Byte</li>
 *     <li>V (version, 版本): 1Byte</li>
 *     <li>LEN (content length, 消息长度): 4Byte</li>
 *     <li>T (message type, 消息类型): 1Byte</li>
 *     <li>Z (Compress, 压缩类型): 1Byte</li>
 *     <li>C (codec, 序列化类型): 1Byte</li>
 *     <li>ID (sequenceId, 请求ID): 4Byte</li>
 * </ul>
 *
 * @author KHighness
 * @since 2021-09-11
 * @see <a href="https://zhuanlan.zhihu.com/p/95621344">LengthFieldBasedFrameDecoder解码器</a>
 */

@Slf4j
public class RpcMessageEncoder extends MessageToByteEncoder<RpcMessage> {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage rpcMessage, ByteBuf out) {
        try {
            out.writeBytes(RpcConstants.MAGIC_NUMBER);
            out.writeByte(RpcConstants.VERSION);
            // leave a place to write the value of full length
            out.writerIndex(out.writerIndex() + 4);
            byte messageType = rpcMessage.getMessageType();
            out.writeByte(messageType);
            out.writeByte(rpcMessage.getCodec());
            out.writeByte(CompressTypeEnum.GZIP.getCode());
            out.writeInt(ATOMIC_INTEGER.getAndIncrement());
            // build full length
            byte[] bodyBytes = null;
            int fullLength = RpcConstants.HEAD_LENGTH;
            // if messageType is not heartbeat message,fullLength = head length + body length
            if (messageType != RpcConstants.HEARTBEAT_REQUEST_TYPE
                    && messageType != RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                // serialize the object
                String codecName = SerializationTypeEnum.getName(rpcMessage.getCodec());
                log.info("codec name: [{}] ", codecName);
                Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class)
                        .getExtension(codecName);
                bodyBytes = serializer.serialize(rpcMessage.getData());
                // compress the bytes
                String compressName = CompressTypeEnum.getName(rpcMessage.getCompress());
                Compress compress = ExtensionLoader.getExtensionLoader(Compress.class)
                        .getExtension(compressName);
                bodyBytes = compress.compress(bodyBytes);
                fullLength += bodyBytes.length;
            }

            if (bodyBytes != null) {
                out.writeBytes(bodyBytes);
            }
            int writeIndex = out.writerIndex();
            out.writerIndex(writeIndex - fullLength + RpcConstants.MAGIC_NUMBER.length + 1);
            out.writeInt(fullLength);
            out.writerIndex(writeIndex);
        } catch (Exception e) {
            log.error("Encode request error!", e);
        }
    }


}

