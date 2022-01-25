package br.com.zup.gerenciadorDePostagem.postagem;

import br.com.zup.gerenciadorDePostagem.enums.Area;
import br.com.zup.gerenciadorDePostagem.enums.Tema;
import br.com.zup.gerenciadorDePostagem.enums.Tipo;
import br.com.zup.gerenciadorDePostagem.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "postagens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String titulo;
    private String descricao;
    @Column(nullable = false, unique = true)
    private String link;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tema tema;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Area areaAtuacao;
    private Integer likes;
    private Integer deslikes;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Usuario autorPostagem;
    @Column(nullable = false)
    private LocalDate dataDeCadastro;

}
