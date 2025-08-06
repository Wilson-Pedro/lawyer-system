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
    email: string;
    matricula: string;
    periodo: string;
    senha: string;
};

const { width } = Dimensions.get('window');

export default function CadastrarEstagiario() {
  const [form, setForm] = useState<FormTypes>({
    nome: '',
    email: '',
    matricula: '',
    periodo: '',
    senha: '',
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
    { label: 'E-mail', field: 'email', keyboardType: 'email-address' },
    { label: 'Matrícula', field: 'matricula' },
    { label: 'Perio', field: 'periodo' },
    { label: 'Senha', field: 'senha' },
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
