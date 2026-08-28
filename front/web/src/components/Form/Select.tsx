// src/components/Form/Select.tsx
import React from "react";
import { useFormContext } from "react-hook-form";
import { Form } from "react-bootstrap";

interface SelectProps extends Omit<React.SelectHTMLAttributes<HTMLSelectElement>, 'size'>{
  name: string;
  label: string;
  options: { value: string, label: string}[];
}

export function Select({ name, label, options, ...rest }: SelectProps) {
  const { register, formState: { errors } } = useFormContext();
  const erro = errors[name]?.message as string;

  return (
    <Form.Group className="mb-3" controlId={name}>
      <Form.Label>{label}</Form.Label>
      
      <Form.Select {...register(name)} {...rest} isInvalid={!!erro}>
        <option value="" disabled hidden>Selecione uma opção...</option>
        
        {options.map((opcao) => (
          <option key={opcao.value} value={opcao.value}>
            {opcao.label}
          </option>
        ))}
      </Form.Select>

      <Form.Control.Feedback type="invalid">
        {erro}
      </Form.Control.Feedback>
    </Form.Group>
  );
}