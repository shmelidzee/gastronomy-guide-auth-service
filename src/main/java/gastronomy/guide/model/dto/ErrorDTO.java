package gastronomy.guide.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String message;
    private List<String> details;

    public ErrorDTO(String message) {
        this.message = message;
    }
}
