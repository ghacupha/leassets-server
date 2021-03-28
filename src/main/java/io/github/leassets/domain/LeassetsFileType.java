package io.github.leassets.domain;

import io.github.leassets.domain.enumeration.LeassetsFileMediumTypes;
import io.github.leassets.domain.enumeration.LeassetsFileModelType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A LeassetsFileType.
 */
@Entity
@Table(name = "leassets_file_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "leassetsfiletype")
public class LeassetsFileType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "leassets_file_type_name", nullable = false, unique = true)
    private String leassetsFileTypeName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "leassets_file_medium_type", nullable = false)
    private LeassetsFileMediumTypes leassetsFileMediumType;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "file_template")
    private byte[] fileTemplate;

    @Column(name = "file_template_content_type")
    private String fileTemplateContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "leassetsfile_type")
    private LeassetsFileModelType leassetsfileType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeassetsFileTypeName() {
        return leassetsFileTypeName;
    }

    public LeassetsFileType leassetsFileTypeName(String leassetsFileTypeName) {
        this.leassetsFileTypeName = leassetsFileTypeName;
        return this;
    }

    public void setLeassetsFileTypeName(String leassetsFileTypeName) {
        this.leassetsFileTypeName = leassetsFileTypeName;
    }

    public LeassetsFileMediumTypes getLeassetsFileMediumType() {
        return leassetsFileMediumType;
    }

    public LeassetsFileType leassetsFileMediumType(LeassetsFileMediumTypes leassetsFileMediumType) {
        this.leassetsFileMediumType = leassetsFileMediumType;
        return this;
    }

    public void setLeassetsFileMediumType(LeassetsFileMediumTypes leassetsFileMediumType) {
        this.leassetsFileMediumType = leassetsFileMediumType;
    }

    public String getDescription() {
        return description;
    }

    public LeassetsFileType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFileTemplate() {
        return fileTemplate;
    }

    public LeassetsFileType fileTemplate(byte[] fileTemplate) {
        this.fileTemplate = fileTemplate;
        return this;
    }

    public void setFileTemplate(byte[] fileTemplate) {
        this.fileTemplate = fileTemplate;
    }

    public String getFileTemplateContentType() {
        return fileTemplateContentType;
    }

    public LeassetsFileType fileTemplateContentType(String fileTemplateContentType) {
        this.fileTemplateContentType = fileTemplateContentType;
        return this;
    }

    public void setFileTemplateContentType(String fileTemplateContentType) {
        this.fileTemplateContentType = fileTemplateContentType;
    }

    public LeassetsFileModelType getLeassetsfileType() {
        return leassetsfileType;
    }

    public LeassetsFileType leassetsfileType(LeassetsFileModelType leassetsfileType) {
        this.leassetsfileType = leassetsfileType;
        return this;
    }

    public void setLeassetsfileType(LeassetsFileModelType leassetsfileType) {
        this.leassetsfileType = leassetsfileType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeassetsFileType)) {
            return false;
        }
        return id != null && id.equals(((LeassetsFileType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeassetsFileType{" +
            "id=" + getId() +
            ", leassetsFileTypeName='" + getLeassetsFileTypeName() + "'" +
            ", leassetsFileMediumType='" + getLeassetsFileMediumType() + "'" +
            ", description='" + getDescription() + "'" +
            ", fileTemplate='" + getFileTemplate() + "'" +
            ", fileTemplateContentType='" + getFileTemplateContentType() + "'" +
            ", leassetsfileType='" + getLeassetsfileType() + "'" +
            "}";
    }
}
