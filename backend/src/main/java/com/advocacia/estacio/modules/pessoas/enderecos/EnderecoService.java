package com.advocacia.estacio.modules.pessoas.enderecos;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

	private final EnderecoRepository enderecoRepository;

	public EnderecoService(EnderecoRepository repository) {
		this.enderecoRepository = repository;
	}

	public Endereco cadastrarEndereco(EnderecoDTO.Request dados) {
		if (dados == null) {
			return null;
		}

		// TODO: adicionar validacao por Cep no futuro, talvez.
		Endereco endereco = Endereco.builder()
				.cidade(dados.cidade())
				.cep(dados.cep())
				.bairro(dados.bairro())
				.rua(dados.rua())
				.numeroDaCasa(dados.numeroDaCasa())
				.build();

		return enderecoRepository.save(endereco);
	}

//	public Endereco salvar(AdvogadoDto advogadoDto) {
//		return enderecoRepository.save(new Endereco(advogadoDto));
//	}

	public EnderecoDTO.Response buscarPorId(Long id) {
		var endereco = enderecoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
		return new EnderecoDTO.Response(endereco);
	}

//	public Endereco atualizar(Long id, Endereco endereco) {
//		Endereco enderecoAtualizado = buscarPorId(id);
//		enderecoAtualizado.setId(id);
//		enderecoAtualizado.setCidade(endereco.getCidade());
//		enderecoAtualizado.setBairro(endereco.getBairro());
//		enderecoAtualizado.setRua(endereco.getRua());
//		enderecoAtualizado.setNumeroDaCasa(endereco.getNumeroDaCasa());
//		enderecoAtualizado.setCep(endereco.getCep());
//		return enderecoRepository.save(enderecoAtualizado);
//	}
}
