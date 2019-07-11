package cn.group.program.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "describest")
public class Describet implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "own")
    private Long own;

    @Column(name = "photoPath")
    private String photoPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getOwn() {
        return own;
    }

    public void setOwn(Long own) {
        this.own = own;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
