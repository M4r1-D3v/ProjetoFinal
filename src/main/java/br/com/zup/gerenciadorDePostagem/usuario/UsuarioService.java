package br.com.zup.gerenciadorDePostagem.usuario;

import br.com.zup.gerenciadorDePostagem.exceptions.EmailJaCadastradoException;
import br.com.zup.gerenciadorDePostagem.exceptions.NaoExistemUsuariosCadastradosException;
import br.com.zup.gerenciadorDePostagem.exceptions.UsuarioNaoAutorizadoException;
import br.com.zup.gerenciadorDePostagem.exceptions.UsuarioNaoCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;


    public Usuario cadastrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        String senhaEscondida = encoder.encode(usuario.getSenha());

        if (usuarioExistente.isPresent()) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        usuario.setSenha(senhaEscondida);

        return usuarioRepository.save(usuario);
    }


    public Usuario atualizarUsuario(String id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new UsuarioNaoCadastradoException("O usuário não existe, favor Cadastrar");
        }
        Usuario usuarioParaAtualizar = usuarioOptional.get();
        usuarioParaAtualizar.setEmail(usuarioAtualizado.getEmail());
        usuarioParaAtualizar.setSenha(usuarioAtualizado.getSenha());
        usuarioParaAtualizar.setNome(usuarioAtualizado.getNome());

        usuarioRepository.save(usuarioParaAtualizar);


        return usuarioParaAtualizar;
    }

    public List<Usuario> exibirUsuarios() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            throw new NaoExistemUsuariosCadastradosException("Não há usuários cadastrados");
        }
        return usuarios;
    }

    public void deletarUsuario(String email, String idUsuario) {
        Usuario usuario = verificarUsuario(email, idUsuario);

        usuarioRepository.deleteById(usuario.getId());
    }

    private Usuario verificarUsuario(String email) {
        Optional<Usuario> usuarioCadastrado = usuarioRepository.findByEmail(email);

        if (usuarioCadastrado.isPresent()) {
            return usuarioCadastrado.get();
        } else {
            throw new NaoExistemUsuariosCadastradosException("Usuario não cadastrado");
        }
    }



}
