package com.vaadin.example.domain.util.pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageJacksonModule extends Module {
    @Override
    public String getModuleName() {
        return "PageJacksonModule";
    }

    @Override
    public Version version() {
        return new Version(0,1,0, "", null,null);
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        setupContext.setMixInAnnotations(Page.class, PageMixIn.class);
    }

    @JsonDeserialize(as = PageImplSimple.class)
    private interface PageMixIn{ }

    public static class PageImplSimple<T>  implements Page<T> {

        private final Page<T> delegate;

        public PageImplSimple(
                @JsonProperty("content") List<T> content,
                @JsonProperty("page")int number,
                @JsonProperty("size") int size,
                @JsonProperty("totalElements") long totalElements){
            delegate = new PageImpl<>(content, PageRequest.of(number, size), totalElements);
        }

        @JsonProperty
        @Override
        public int getTotalPages() {
            return this.delegate.getTotalPages();
        }

        @JsonProperty
        @Override
        public long getTotalElements() {
            return this.delegate.getTotalElements();
        }

        @JsonIgnore
        @Override
        public <U> Page<U> map(Function<? super T, ? extends U> function) {
            return this.delegate.map(function);
        }

        @JsonProperty
        @Override
        public int getNumber() {
            return this.delegate.getNumber();
        }

        @JsonProperty
        @Override
        public int getSize() {
            return this.delegate.getSize();
        }

        @JsonProperty
        @Override
        public int getNumberOfElements() {
            return this.delegate.getNumberOfElements();
        }

        @JsonProperty
        @Override
        public List<T> getContent() {
            return this.delegate.getContent();
        }

        @JsonProperty
        @Override
        public boolean hasContent() {
            return this.delegate.hasContent();
        }

        @JsonIgnore
        @Override
        public Sort getSort() {
            return this.delegate.getSort();
        }

        @JsonProperty
        @Override
        public boolean isFirst() {
            return this.delegate.isFirst();
        }

        @JsonProperty
        @Override
        public boolean isLast() {
            return this.delegate.isLast();
        }

        @JsonIgnore
        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @JsonIgnore
        @Override
        public boolean hasPrevious() {
            return this.delegate.hasPrevious();
        }

        @JsonIgnore
        @Override
        public org.springframework.data.domain.Pageable nextPageable() {
            return this.delegate.nextPageable();
        }

        @JsonIgnore
        @Override
        public org.springframework.data.domain.Pageable previousPageable() {
            return this.delegate.previousPageable();
        }

        @JsonIgnore
        @NotNull
        @Override
        public Iterator<T> iterator() {
            return this.delegate.iterator();
        }

        @JsonIgnore
        @Override
        public Pageable getPageable() {
            return this.delegate.getPageable();
        }
    }

}
