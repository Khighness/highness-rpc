package top.parak.remoting.dto;

import lombok.*;

/**
 * @author KHighness
 * @since 2021-08-22
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {
    /**
     * RPC message type
     */
    private byte messageType;

    /**
     * Serialization type
     */
    private byte codec;

    /**
     * Compress type
     */
    private byte compress;

    /**
     * Request ID
     */
    private int requestId;

    /**
     * Request data
     */
    private Object data;
}
