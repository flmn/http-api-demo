package tech.jitao.httpapidemo.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.jitao.httpapidemo.common.MessageException;
import tech.jitao.httpapidemo.common.dto.PageDto;
import tech.jitao.httpapidemo.dto.DocumentDto;
import tech.jitao.httpapidemo.entity.Document;
import tech.jitao.httpapidemo.repository.DocumentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private static final int APP_PAGE_SIZE = 3;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DocumentRepository documentRepository;
    private final AccountService accountService;

    public DocumentService(DocumentRepository documentRepository,
                           AccountService accountService) {
        this.documentRepository = documentRepository;
        this.accountService = accountService;
    }

    // shared
    public DocumentDto getDocument(long id) throws MessageException {
        return toDto(get(id), true);
    }

    // for app only
    public PageDto<DocumentDto> listDocuments(String nextPageToken) throws MessageException {
        int currentPage = NumberUtils.toInt(nextPageToken, 1);

        List<DocumentDto> items = documentRepository.findAll(currentPage, APP_PAGE_SIZE).stream().map(d -> toDto(d, false)).collect(Collectors.toList());

        if (items.isEmpty()) {
            return PageDto.empty();
        }

        PageDto<DocumentDto> dto = PageDto.of(items);
        if (items.size() == APP_PAGE_SIZE) { // 可能有下一页
            dto.setNextPageToken(String.valueOf(currentPage + 1));
        }

        return dto;
    }

    // for web only
    public PageDto<DocumentDto> listDocuments(int currentPage, int pageSize) throws MessageException {
        List<DocumentDto> items = documentRepository.findAll(currentPage, pageSize).stream().map(d -> toDto(d, false)).collect(Collectors.toList());

        if (items.isEmpty()) {
            return PageDto.empty();
        }

        PageDto<DocumentDto> dto = PageDto.of(items);
        dto.setTotal(documentRepository.count());

        return dto;
    }

    public DocumentDto createDocument(String title,
                                      String content,
                                      BigDecimal price,
                                      Integer status,
                                      long userId) throws MessageException {
        Document document = new Document();
        document.setOwner(userId);
        document.setTitle(title);
        document.setContent(content);
        document.setPrice(price);
        document.setStatus(status);
        document.setCreateTime(LocalDateTime.now());

        documentRepository.save(document);

        logger.info("Document {} created by user {}.", document.getId(), userId);

        return toDto(document, true);
    }

    public DocumentDto updateDocument(long id,
                                      String title,
                                      String content,
                                      BigDecimal price,
                                      Integer status,
                                      long userId) throws MessageException {
        Document document = get(id);
        document.setTitle(title);
        document.setContent(content);
        document.setPrice(price);
        document.setStatus(status);
        document.setUpdateTime(LocalDateTime.now());

        logger.info("Document {} updated by user {}.", id, userId);

        return toDto(document, true);
    }

    public void deleteDocument(long id,
                               long userId) throws MessageException {
        Document document = get(id);

        documentRepository.delete(document);

        logger.info("Document {} deleted by user {}.", id, userId);
    }

    private Document get(long id) throws MessageException {
        Optional<Document> opt = documentRepository.findById(id);
        if (!opt.isPresent()) {
            throw new MessageException("找不到文档");
        }

        return opt.get();
    }

    private DocumentDto toDto(Document document, boolean detail) {
        DocumentDto dto = new DocumentDto(document, detail);
        dto.setOwner(accountService.get(document.getOwner()));

        return dto;
    }
}
