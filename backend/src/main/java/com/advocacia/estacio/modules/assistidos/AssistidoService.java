package com.advocacia.estacio.modules.assistidos;

import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoService;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.modules.pessoas.enderecos.Endereco;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssistidoService {

	private final AssistidoRepository assistidoRepository;
	private final EnderecoService enderecoService;

	public AssistidoService(AssistidoRepository assistidoRepository, EnderecoService enderecoService) {
		this.assistidoRepository = assistidoRepository;
		this.enderecoService = enderecoService;
	}

	@Transactional
	public AssistidoDTO.Response cadastrar(AssistidoDTO.CreateRequest dados) {
		Endereco endereco = enderecoService.cadastrarEndereco(dados.endereco());

		Pessoa pessoa = Pessoa.builder()
				.nome(dados.nome())
				.email(dados.email())
				.telefone(dados.telefone())
				.endereco(endereco)
				.build();

		Assistido assistido = Assistido.builder()
				.pessoa(pessoa)
				.matricula(dados.matricula())
				.estadoCivil(dados.estadoCivil())
				.nacionalidade(dados.nacionalidade())
				.naturalidade(dados.naturalidade())
				.profissao(dados.profissao())
				.build();

		var assistidoSalvo = assistidoRepository.save(assistido);
		return new AssistidoDTO.Response(assistidoSalvo);
	}

	@Transactional(readOnly = true)
	public Page<AssistidoDTO.ListResponse> listar(Pageable pageable) {
		return assistidoRepository.findAll(pageable).map(AssistidoDTO.ListResponse::new);
	}

	@Transactional(readOnly = true)
	public AssistidoDTO.Response buscarPorId(Long id) {
		var assistido = assistidoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Assistido não encontrado!"));
		return new AssistidoDTO.Response(assistido);
	}


//
//	public Assistido atualizar(Long id, AssistidoDto assistidoDto) {
//		Assistido assistido = buscarPorId(id);
//		assistido.setId(id);
//		assistido.setNome(assistidoDto.getNome());
//		assistido.setMatricula(assistidoDto.getMatricula());
//		assistido.setTelefone(assistidoDto.getTelefone());
//		assistido.setEmail(assistidoDto.getEmail());
//		assistido.setProfissao(assistidoDto.getProfissao());
//		assistido.setNacionalidade(assistidoDto.getNacionalidade());
//		assistido.setNaturalidade(assistidoDto.getNaturalidade());
//		assistido.setEstadoCivil(EstadoCivil.toEnum(assistidoDto.getEstadoCivil()));
//		enderecoService.atualizar(assistido.getEndereco().getId(), new Endereco(assistidoDto));
//		return assistidoRepository.save(assistido);
//	}

}
