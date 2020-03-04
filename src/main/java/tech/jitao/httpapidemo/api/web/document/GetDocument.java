package tech.jitao.httpapidemo.api.web.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.jitao.httpapidemo.common.ApiResult;
import tech.jitao.httpapidemo.common.MessageException;
import tech.jitao.httpapidemo.common.request.IdRequest;
import tech.jitao.httpapidemo.service.DocumentService;

@RestController(GetDocument.PATH)
@CrossOrigin
public class GetDocument {
    static final String PATH = "/web/document/get-document";

    @Autowired
    private DocumentService documentService;

    @PostMapping(PATH)
    public ApiResult process(@Validated @RequestBody IdRequest request) {
        try {
            return ApiResult.okWithData(documentService.getDocument(request.getId()));
        } catch (MessageException e) {
            return ApiResult.error(e.getMessage());
        }
    }
}
