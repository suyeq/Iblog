package com.suye.iblog.moder;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;

    @NotEmpty(message = "路径不能为空")
    @Column(nullable = true)
    private String path;

    protected  File(){

    }

    public File(Long id,String name,String path){
        this.id=id;
        this.name=name;
        this.path=path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
