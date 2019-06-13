package cn.group.program.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer")
    private String answer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "question")
    private Collection<Describe> describes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Collection<Describe> getDescribes() {
        return describes;
    }

    public void setDescribes(Collection<Describe> describes) {
        this.describes = describes;
    }
}
