package main.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_comment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when parent_id is null then 0 else 1 end")
@DiscriminatorValue(value = "0")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;
}