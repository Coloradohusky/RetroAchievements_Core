package coloradohusky.rajavaintegration.network.response;

/**
 * Generic API response from RA
 * @param <T> Type of the response
 */
public class ApiResponse<T> {
    private final T response;
    private final String failure;

    public ApiResponse(T response, String failure) {
        this.response = response;
        this.failure = failure;
    }

    public T getResponse() {
        return response;
    }

    public String getFailure() {
        return failure;
    }

    public boolean isSuccess() {
        return failure == null || failure.isEmpty();
    }

    public static <T> ApiResponse<T> succeeded(T response) {
        return new ApiResponse<>(response, "");
    }

    public static <T> ApiResponse<T> failed(String failure) {
        return new ApiResponse<>(null, failure);
    }
}

