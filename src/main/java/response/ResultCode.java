package response;
public enum ResultCode {
    OK(200),
    OK_NO_CONTENT(204),
    FAILED(400),// miss param
    UNAUTHORIZED(401),
    FORBIDDEND(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);


    public Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }
}
