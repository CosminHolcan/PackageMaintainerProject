package ro.arc.packageManager.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "packageWithPackageMaintainers",
                attributeNodes = @NamedAttributeNode(value = "packageMaintainers"))
})
@Entity
@Table(name = "apackage")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Package extends BaseEntity<Long>  {
    private String name;
    private String description;
    private String sourceRepo;
    private String license;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "apackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PackageMaintainer> packageMaintainers = new ArrayList<>();


    public void addMaintainer(Maintainer maintainer) {
        PackageMaintainer packageMaintainer = new PackageMaintainer();
        packageMaintainer.setMaintainer(maintainer);
        packageMaintainer.setApackage(this);
        packageMaintainers.add(packageMaintainer);
    }

    public void addImportance(Maintainer maintainer, Integer importance) {
        PackageMaintainer packageMaintainer = new PackageMaintainer();
        packageMaintainer.setMaintainer(maintainer);
        packageMaintainer.setImportance(importance);
        packageMaintainer.setApackage(this);
        packageMaintainers.add(packageMaintainer);
    }

    public void deleteMaintainer(Long ID)
    {
        PackageMaintainer packageMaintainer = this.packageMaintainers.stream()
                .filter(p -> p.getMaintainer().getId().equals(ID))
                .findFirst()
                .get();
        this.packageMaintainers.remove(packageMaintainer);
    }

    public void updateImportance(Long maintainerID, Integer newImportance)
    {
        PackageMaintainer packageMaintainer = this.packageMaintainers.stream()
                .filter(p -> p.getMaintainer().getId().equals(maintainerID))
                .findFirst()
                .get();
        packageMaintainer.setImportance(newImportance);
    }

    public void prepareToBeDeleted(){
        this.packageMaintainers
                .forEach(p -> p.getMaintainer().deletePackage(this.getId()));

        this.packageMaintainers.clear();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Package that = (Package) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Package{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sourceRepo='" + sourceRepo + '\'' +
                ", license='" + license + '\'' +
                '}'+super.toString();
    }
}
