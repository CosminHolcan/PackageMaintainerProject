package ro.arc.packageManager.core.domain;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

import static java.lang.Long.parseLong;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "packageVersionWithPackage",
                attributeNodes = @NamedAttributeNode(value = "aPackage"))
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PackageVersion extends BaseEntity<Long> {
    private String versionNumber;
    private String startingDate;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Package aPackage;
}