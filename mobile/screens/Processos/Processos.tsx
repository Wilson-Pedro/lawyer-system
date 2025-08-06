import React from 'react';
import { View, Text, StyleSheet, FlatList, Dimensions, ListRenderItem } from 'react-native';

const { width } = Dimensions.get('window');

type ProcessoType = {
    id:string;
    numero:string;
    assunto:string;
    prazo:string;
    responsavel:string;
}

const processos:ProcessoType[]  = [
  {
    id: '1',
    numero: '0001234-56.2023.8.01.0001',
    assunto: 'Pensão alimentícia',
    prazo: '20/05/2025',
    responsavel: 'Ana Souza',
  },
  {
    id: '2',
    numero: '0004321-98.2023.8.01.0002',
    assunto: 'Direito trabalhista',
    prazo: '30/06/2025',
    responsavel: 'João Lima',
  },
  {
    id: '3',
    numero: '0008765-44.2024.8.01.0003',
    assunto: 'Inventário',
    prazo: '15/07/2025',
    responsavel: 'Mariana Rocha',
  },
];

export default function Processos() {
    
  const renderCard: ListRenderItem<ProcessoType> = ({ item }) => (
      <View style={styles.card}>
          <Text style={styles.numero}>Nº Processo: {item.numero}</Text>
          <Text style={styles.assunto}>Assunto: {item.assunto}</Text>
          <Text style={styles.prazo}>Prazo: {item.prazo}</Text>
          <Text style={styles.responsavel}>Responsável: {item.responsavel}</Text>
      </View>
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Processos em Andamento</Text>
      <FlatList
        data={processos}
        keyExtractor={(item) => item.id}
        renderItem={renderCard}
        contentContainerStyle={styles.list}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#eef2f7',
    paddingTop: 40,
    paddingHorizontal: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: '800',
    color: '#2a3a7f',
    marginBottom: 20,
    textAlign: 'center',
  },
  list: {
    paddingBottom: 30,
  },
  card: {
    backgroundColor: '#ffffff',
    borderRadius: 10,
    padding: 16,
    marginBottom: 16,
    elevation: 3,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.12,
    shadowRadius: 4,
    borderLeftWidth: 5,
    borderLeftColor: '#2a3a7f',
  },
  numero: {
    fontSize: 16,
    fontWeight: '700',
    marginBottom: 6,
    color: '#1a2e66',
  },
  assunto: {
    fontSize: 15,
    color: '#333',
    marginBottom: 4,
  },
  prazo: {
    fontSize: 15,
    color: '#e67e22',
    marginBottom: 4,
  },
  responsavel: {
    fontSize: 15,
    color: '#34495e',
  },
});
