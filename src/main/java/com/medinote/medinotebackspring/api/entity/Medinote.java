package com.medinote.medinotebackspring.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEDINOTES", indexes=@Index(name="idx_user_id",columnList = "USER_ID"))
public class Medinote {
//    @JsonIgnore
    @Id
    @Column(name = "MEDINOTE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medinoteSeq;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    @Column(name = "MEDINOTE_TITLE")
    @Size(max = 200)
    private String medinoteTitle;

    @Column(name = "MEDINOTE_TEXT")
    @Lob
    private String medinoteText;

    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

    @Column(name = "TRANSFORM_FIN_YN")
    @NotNull
    @Size(min = 1, max = 1)
    private String transformFin;

    @OneToOne(mappedBy = "medinote", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedinoteMetadata medinoteMetadata;

    public Medinote(
            String medinoteTitle,
            User user
    ) {
        this.medinoteTitle = medinoteTitle;
        this.userId = user;
        this.medinoteText = medinoteText;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.transformFin = "N";
    }
}
