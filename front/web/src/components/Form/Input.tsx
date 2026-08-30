// src/components/Form/Input.tsx
import React from 'react';
import { useFormContext } from 'react-hook-form';
import { Form } from 'react-bootstrap';

interface InputProps extends Omit<
  React.InputHTMLAttributes<HTMLInputElement>,
  'size' | 'value'
> {
  name: string; // O name é obrigatório para conectar ao React Hook Form
  label: string;
  size?: 'sm' | 'lg'; // Tamanhos do bootstrap
}

export function Input({ name, label, size, ...rest }: InputProps) {
  // Pega os métodos do formulário pai automaticamente!
  const {
    register,
    formState: { errors },
  } = useFormContext();
  // Verifica se há um erro específico para este input
  const erro = errors[name]?.message as string;

  return (
    <Form.Group>
      <Form.Label>{label}</Form.Label>

      <Form.Control
        {...register(name)} // O React Hook Form assume o controle do input
        size={size}
        {...rest}
        isInvalid={!!erro}
      />

      {/* Mensagem de erro oficial do Bootstrap (só aparece se isInvalid for true) */}
      <Form.Control.Feedback type="invalid">{erro}</Form.Control.Feedback>
    </Form.Group>
  );
}
