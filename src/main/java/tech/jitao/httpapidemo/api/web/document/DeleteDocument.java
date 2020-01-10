package tech.jitao.httpapidemo.api.web.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.jitao.httpapidemo.common.ApiResult;
import tech.jitao.httpapidemo.common.MessageException;
import tech.jitao.httpapidemo.common.request.IdRequest;
import tech.jitao.httpapidemo.service.DocumentService;

@RestController(DeleteDocument.PATH)
public class DeleteDocument {
    static final String PATH = "/web/document/delete-document";

    @Autowired
    private DocumentService documentService;

    @PostMapping(PATH)
    public ApiResult process(@Validated @RequestBody IdRequest request) {
        try {
            documentService.deleteDocument(request.getId());

            return ApiResult.ok();
        } catch (MessageException e) {
            return ApiResult.error(e.getMessage());
        }
    }
}
