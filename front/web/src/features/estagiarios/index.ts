import { estagiariosService } from '../../features/estagiarios/api/estagiariosService';
import { EstagiarioRequest } from '../../features/estagiarios/types/EstagiarioRequest';

export * from './types/EstagiarioRequest';

// 2. Exporta o service para caso outra tela queira buscar dados de estagiários
export * from './api/estagiariosService';

// 3. Exporta as telas/componentes principais
// export { default as CadastrarEstagiario } from './pages/CadastrarEstagiario';