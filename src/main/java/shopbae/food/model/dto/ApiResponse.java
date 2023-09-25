package shopbae.food.model.dto;

public class ApiResponse {
    private Object data;
    private String message;

    public ApiResponse(Object data) {
        this.data = data;
        this.message = null;
    }

    public ApiResponse(String message) {
        this.data = null;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
