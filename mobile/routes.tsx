import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StackParamList  } from './types';

import Home from './screens/Home/Home';
import Admin from './screens/HomeAdmin/HomeAdmin';

const Stack = createNativeStackNavigator<StackParamList>();

export default function Routes() {
    return(
        <Stack.Navigator initialRouteName="Home">
            <Stack.Screen name="Home" component={Home} options={{ title: 'Início' }}/>
            <Stack.Screen name="Admin" component={Admin} options={{ title: 'Painel do Administrador' }}/>
        </Stack.Navigator>
    )
}