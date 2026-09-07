package com.advocacia.estacio.modules.pessoas.enderecos;

public interface EnderecoDTO {
    record Request(
            String rua,
            String numeroDaCasa,
            String bairro,
            String cidade,
            String cep
    ) {
    }

    record Response(
            Long id,
            String rua,
            String numeroDaCasa,
            String bairro,
            String cidade,
            String cep
    ) {
        // Usar quando for buscar o endereço de alguma pessoa
        public static Response from(Endereco endereco) {
            if (endereco == null) {
                return null;
            }
            return new Response(
                    endereco.getId(),
                    endereco.getRua(),
                    endereco.getNumeroDaCasa(),
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getCep());
        }

        public Response(Endereco endereco) {
            this(
                    endereco.getId(),
                    endereco.getRua(),
                    endereco.getNumeroDaCasa(),
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getCep()
            );
        }
    }
}
