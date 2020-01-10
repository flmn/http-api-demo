package tech.jitao.httpapidemo.repository;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import tech.jitao.httpapidemo.entity.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentRepository {
    public void save(Document document) {
        document.setId(RandomUtils.nextLong());
    }

    public void delete(Document document) {
    }

    public Optional<Document> findById(long id) {
        if (id == 1) {
            return Optional.empty();
        }

        Document document = new Document();
        document.setId(id);
        document.setOwner(RandomUtils.nextLong());
        document.setTitle(RandomStringUtils.randomAlphanumeric(20));
        document.setContent(RandomStringUtils.randomAlphanumeric(1024));
        document.setPrice(new BigDecimal("2.4"));
        document.setStatus(1);
        document.setCreateTime(LocalDateTime.now());
        document.setUpdateTime(LocalDateTime.now());

        return Optional.of(document);
    }

    public long count() {
        return 5l;
    }

    public List<Document> findAll(int currentPage, int pageSize) {
        List<Document> items = Lists.newArrayList();

        Optional<Document> opt = findById(2);
        opt.ifPresent(items::add);

        opt = findById(3);
        opt.ifPresent(items::add);

        opt = findById(4);
        opt.ifPresent(items::add);

        return items;
    }
}
