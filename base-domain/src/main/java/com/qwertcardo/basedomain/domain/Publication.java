package com.qwertcardo.basedomain.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qwertcardo.basedomain.domain.enums.PublicationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publication")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private Date creationDate;

    @Enumerated(EnumType.ORDINAL)
    private PublicationTypeEnum type;

    @OneToMany(mappedBy = "publication")
    @JsonIgnoreProperties({"publication"})
    private List<PhotoFile> files;

    @OneToMany(mappedBy = "reference")
    @JsonIgnoreProperties({"reference"})
    private List<Publication> comments;

    @OneToMany(mappedBy = "publication")
    @JsonIgnoreProperties({"publication"})
    private List<Like> likes;

    @ManyToOne
    @JoinColumn(name = "creator_id", foreignKey = @ForeignKey(name = "publication_user_fk"))
    private User creator;

    @ManyToOne
    @JoinColumn(name = "reference", foreignKey = @ForeignKey(name = "publication_reference_fk"))
    @JsonIgnoreProperties({"files", "comments", "likes"})
    private Publication reference;

    @Column(name = "visualizations")
    private Long visualizations;
}
