package io.zethange.entity.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class NewsCategory extends PanacheEntity {
    private String title;

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("category")
    private List<News> news;
}
