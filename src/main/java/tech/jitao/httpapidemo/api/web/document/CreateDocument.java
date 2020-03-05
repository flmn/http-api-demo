package tech.jitao.httpapidemo.api.web.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.jitao.httpapidemo.common.ApiResult;
import tech.jitao.httpapidemo.common.MessageException;
import tech.jitao.httpapidemo.config.RequestAttributes;
import tech.jitao.httpapidemo.service.DocumentService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@RestController(CreateDocument.PATH)
@CrossOrigin
public class CreateDocument {
    static final String PATH = "/web/document/create-document";

    @Autowired
    private DocumentService documentService;

    @PostMapping(PATH)
    public ApiResult process(@Validated @RequestBody Request request,
                             @RequestAttribute(RequestAttributes.USER_ID) long userId) {
        try {
            return ApiResult.okWithData(documentService.createDocument(request.getTitle(),
                    request.getContent(),
                    request.getPrice(),
                    request.getStatus(),
                    userId));
        } catch (MessageException e) {
            return ApiResult.error(e.getMessage());
        }
    }

    private static class Request {

        @NotBlank
        @Size(max = 32)
        private String title;

        @NotBlank
        @Size(max = 1024)
        private String content;

        @PositiveOrZero
        private BigDecimal price;

        @PositiveOrZero
        private Integer status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
