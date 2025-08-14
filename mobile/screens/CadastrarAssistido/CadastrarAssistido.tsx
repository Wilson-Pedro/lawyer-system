import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
  Dimensions,
  KeyboardTypeOptions,
} from 'react-native';

type FormTypes = {
    nome: string;
    cpf: string;
    telefone: string;
    email: string;
    cidade: string;
    bairro: string;
    rua: string;
    numeroCasa: string;
    cep: string;
    complemento: string;
};

const { width } = Dimensions.get('window');

export default function CadastrarAssistido() {
  const [form, setForm] = useState<FormTypes>({
    nome: '',
    cpf: '',
    telefone: '',
    email: '',
    cidade: '',
    bairro: '',
    rua: '',
    numeroCasa: '',
    cep: '',
    complemento: '',
  });

  const handleChange = (field:any, value:any) => {
    setForm({ ...form, [field]: value });
  };

  const handleSubmit = () => {
    Alert.alert('Cadastro enviado!', 'Obrigado por se cadastrar no Núcleo de Práticas Jurídicas.');
  };

  const fields: {
    label:string;
    field: keyof FormTypes;
    keyboardType?: KeyboardTypeOptions;
  }[] = [
    { label: 'Nome completo', field: 'nome' },
    { label: 'Telefone', field: 'telefone', keyboardType: 'phone-pad' },
    { label: 'E-mail', field: 'email', keyboardType: 'email-address' },
    { label: 'Cidade', field: 'cidade' },
    { label: 'Bairro', field: 'bairro' },
    { label: 'Rua', field: 'rua' },
    { label: 'Número da Casa', field: 'numeroCasa', keyboardType: 'numeric' },
    { label: 'CEP', field: 'cep', keyboardType: 'numeric' },
    { label: 'Complemento', field: 'complemento' },
  ];

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Cadastro - Núcleo de Práticas Jurídicas</Text>

      {fields.map(({ label, field, keyboardType }) => (
        <View key={field} style={styles.inputGroup}>
          <Text style={styles.label}>{label}</Text>
          <TextInput
            style={styles.input}
            placeholder={label}
            value={form[field]}
            keyboardType={keyboardType || 'default'}
            onChangeText={(text) => handleChange(field, text)}
            placeholderTextColor="#888"
          />
        </View>
      ))}

      <TouchableOpacity style={styles.button} onPress={handleSubmit}>
        <Text style={styles.buttonText}>Enviar Cadastro</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingVertical: 30,
    paddingHorizontal: 20,
    backgroundColor: '#f4f6f8',
    alignItems: 'center',
  },
  title: {
    fontSize: 22,
    fontWeight: '700',
    color: '#1a2e66',
    marginBottom: 25,
    textAlign: 'center',
  },
  inputGroup: {
    width: width * 0.92,
    marginBottom: 14,
  },
  label: {
    fontSize: 15,
    marginBottom: 6,
    color: '#333',
    fontWeight: '500',
  },
  input: {
    height: 46,
    backgroundColor: '#fff',
    borderColor: '#ccd1d9',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 12,
    fontSize: 15,
    color: '#000',
  },
  button: {
    marginTop: 30,
    marginBottom: 30,
    backgroundColor: '#1a2e66',
    paddingVertical: 14,
    paddingHorizontal: 30,
    borderRadius: 8,
    width: width * 0.92,
    alignItems: 'center',
    elevation: 2,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '600',
  },
});
