package com.qwertcardo.basedomain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photo_file")
public class PhotoFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content64;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "publication_id", foreignKey = @ForeignKey(name = "photo_file_publication_fk"))
    private Publication publication;
}
