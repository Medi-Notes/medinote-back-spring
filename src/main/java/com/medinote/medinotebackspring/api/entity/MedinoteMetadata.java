package com.medinote.medinotebackspring.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEDINOTES_METADATA")
public class MedinoteMetadata {

    @Id
    @Column(name = "MEDINOTE_SEQ")
    @NotNull
    private Long medinoteSeq;

    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "medinoteSeq")
    private Medinote medinote;

    @Column(name = "AUDIO_FILENAME")
    @Size(max = 200)
    private String audioFilename;

    @Column(name = "STT_TEXT")
    @Lob
    private String sttText;

    public MedinoteMetadata(Medinote medinote, String audioFilename) {
        this.medinote = medinote;
        this.setAudioFilename(audioFilename);
    }
}
