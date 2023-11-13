package io.zethange.entity.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class News extends PanacheEntity {
    private String title;

    @ManyToOne
    @JsonIgnoreProperties("news")
    private NewsCategory category;

    private Long views;
}
