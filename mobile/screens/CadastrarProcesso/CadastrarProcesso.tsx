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
    numero: string;
    assunto: string;
    vara: string;
    responsavel: string;
    prazo: string;
};

const { width } = Dimensions.get('window');

export default function CadastrarProcesso() {
  const [form, setForm] = useState<FormTypes>({
    numero: '',
    assunto: '',
    vara: '',
    responsavel: '',
    prazo: '',
  });

  const handleChange = (field: string, value: string) => {
    setForm({ ...form, [field]: value });
  };

  const handleSubmit = () => {
    const { numero, assunto, vara, responsavel, prazo } = form;

    if (!numero || !assunto || !vara || !responsavel || !prazo) {
      Alert.alert('Campos obrigatórios', 'Por favor, preencha todos os campos.');
      return;
    }

    // Aqui você pode enviar os dados para a API/backend
    Alert.alert('Processo cadastrado!', `Número: ${numero}\nAssunto: ${assunto}`);
    setForm({
      numero: '',
      assunto: '',
      vara: '',
      responsavel: '',
      prazo: '',
    });
  };

  const fields: {
    label:string;
    field: keyof FormTypes;
    keyboardType?: KeyboardTypeOptions;
  }[] = [
    { label: 'Número do Processo', field: 'numero' },
    { label: 'Assunto', field: 'assunto' },
    { label: 'Vara', field: 'vara' },
    { label: 'Responsável', field: 'responsavel' },
    { label: 'Prazo (DD/MM/AAAA)', field: 'prazo', keyboardType: 'numeric' },
  ]

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Cadastro de Processo</Text>

      {fields.map(({ label, field, keyboardType }) => (
        <View key={field} style={styles.inputGroup}>
          <Text style={styles.label}>{label}</Text>
          <TextInput
            style={styles.input}
            placeholder={label}
            value={(form as any)[field]}
            onChangeText={(text) => handleChange(field, text)}
            keyboardType={keyboardType || 'default'}
            placeholderTextColor="#999"
          />
        </View>
      ))}

      <TouchableOpacity style={styles.button} onPress={handleSubmit}>
        <Text style={styles.buttonText}>Cadastrar Processo</Text>
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
