import React, { useState } from 'react';
import { Toast, ToastContainer } from 'react-bootstrap';
import { authsService } from '../api/authService';
import { AuthRequest } from '../types/Auth';

import { Input, Button, Form, Container } from '../../../components/Form';

import { UseFormSetError } from 'react-hook-form';
import { tratarErrosBackend } from '../../../utils/errorHelper';

export default function Login() {
  const [toast, setToast] = useState({
    mostrar: false,
    mensagem: '',
    variante: 'success',
  });

  const handleSalvar = async (
    dados: AuthRequest,
    setError: UseFormSetError<AuthRequest>,
  ) => {
    try {
      await authsService.logar(dados);

      setToast({
        mostrar: true,
        mensagem: 'Usuário logado com sucesso!',
        variante: 'success',
      });
    } catch (error: any) {
      console.error(error);

      const mensagem = tratarErrosBackend(error, setError);
      if (mensagem) {
        setToast({ mostrar: true, mensagem, variante: 'danger' });
      }

      // setToast({ mostrar: true, mensagem: "Erro ao cadastrar estagiário.", variante: "danger" });
    }
  };

  return (
    <Container>
      <h2 className="mb-4 text-center">Login</h2>

      <Form<AuthRequest> onSubmit={handleSalvar}>
        <Input name="login" label="Login" />
        <Input name="password" label="Senha" type="password" />

        <Button type="submit">Login</Button>
      </Form>

      <ToastContainer
        position="top-end"
        className="p-3"
        style={{ zIndex: 9999, position: 'fixed' }}
      >
        <Toast
          onClose={() => setToast({ ...toast, mostrar: false })}
          show={toast.mostrar}
          bg={toast.variante}
          delay={4000}
          autohide
        >
          <Toast.Body className="text-white">{toast.mensagem}</Toast.Body>
        </Toast>
      </ToastContainer>
    </Container>
  );
}
