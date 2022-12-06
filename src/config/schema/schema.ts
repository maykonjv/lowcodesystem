export interface ApplicationSchemaType {
  name: string;
  version: string;
  description: string;
}

export interface MenuSchemaType {
  label: string;
  icon?: string;
  path: string;
}

export interface TableColumnType {
  id: string;
  label: string;
  isNumeric?: boolean;
  width?: string;
}

export interface PageSchemaType {
  path: string;
  label: string;
  icon?: string;
  description?: string;
  actions?: Array<'create' | 'update' | 'delete' | 'search' | 'view' | 'custom'>;
  components?: ComponentSchemaType[];
  tablecolumns?: TableColumnType[];
}

export interface ComponentSchemaType {
  id: string;
  label: string;
  type:
    | 'text'
    | 'color'
    | 'number'
    | 'date'
    | 'datetime'
    | 'time'
    | 'select'
    | 'checkbox'
    | 'radio'
    | 'textarea'
    | 'custom';
  description?: string;
  options?: string[];
  isrequired?: boolean;
  isdisabled?: boolean;
  ishidden?: boolean;
  hint?: string;
  placeholder?: string;
  default?: string;
  min?: number;
  max?: number;
  style?: string;
  size: number;
  actions?: Array<'create' | 'update' | 'delete' | 'search' | 'view' | 'custom'>;
}

interface FooterSchemaType {
  year: number;
  description: string;
  version: string;
  socialnetworks: Array<{
    label: string;
    icon: string;
    url: string;
  }>;
}

export const ApplicationSchema: ApplicationSchemaType = {
  name: 'Controle Financeiro',
  version: '1.0.0',
  description: 'React Admin',
};

export const MenusSchema: Array<MenuSchemaType> = [
  { label: 'Home', icon: 'bi bi-house-door', path: '/' },
  { label: 'Categoria', icon: 'bi bi-boxes', path: '/category/search' },
  { label: 'Contas', icon: 'bi bi-person-vcard', path: '/account/create' },
];

export const PagesSchema: Array<PageSchemaType> = [
  { label: 'Home', icon: 'bi bi-house-door', path: '/', actions: ['view'] },
  {
    label: 'Categoria',
    icon: '',
    path: '/category',
    description: 'Cadastro de categoria',
    actions: ['create', 'update', 'delete', 'search', 'view'],
    components: [
      {
        id: 'name',
        label: 'Descrição',
        type: 'text',
        description: 'Descrição da categoria',
        isrequired: true,
        isdisabled: false,
        ishidden: false,
        hint: 'Name',
        placeholder: 'Name',
        default: '',
        min: 0,
        max: 0,
        style: '',
        size: 12,
        actions: ['create', 'update'],
      },
      {
        id: 'color',
        label: 'Cor',
        type: 'color',
        description: 'Cor',
        isrequired: true,
        isdisabled: false,
        ishidden: false,
        hint: 'Name',
        placeholder: 'Name',
        default: '',
        min: 0,
        max: 0,
        style: '',
        size: 12,
        actions: ['create', 'update'],
      },
      {
        id: 'active',
        label: 'Ativo',
        type: 'checkbox',
        description: 'Ativo',
        isrequired: true,
        isdisabled: false,
        ishidden: false,
        hint: 'Name',
        placeholder: 'Name',
        default: '',
        min: 0,
        max: 0,
        style: '',
        size: 12,
        actions: ['create', 'update', 'search'],
      },
    ],
    tablecolumns: [
      {
        id: 'description',
        label: 'Descrição',
      },
      {
        id: 'color',
        label: 'Cor',
      },
      {
        id: 'active',
        label: 'Ativo',
        width: '150px',
      },
      {
        id: '_actions_',
        label: 'Ações',
        width: '200px',
      },
    ],
  },
  {
    label: 'Contas',
    icon: '',
    path: '/account',
    description: 'Gerenciamento de contas',
    actions: ['create', 'update', 'delete', 'search', 'view'],
    components: [
      {
        id: 'description',
        label: 'Descrição',
        type: 'text',
        description: 'Descrição da conta',
        isrequired: true,
        isdisabled: false,
        ishidden: false,
        hint: 'Informe o nome da conta',
        placeholder: 'Conta Itaú',
        default: '',
        min: 0,
        max: 0,
        style: '',
        size: 12,
        actions: ['create', 'update', 'search'],
      },
      {
        id: 'amount',
        label: 'Valor',
        type: 'text',
        description: 'Valor da conta',
        isrequired: true,
        isdisabled: false,
        ishidden: false,
        hint: 'Informe o valor da conta',
        placeholder: 'R$ 100,00',
        default: '',
        min: 0,
        max: 0,
        style: '',
        size: 12,
        actions: ['create', 'update', 'search'],
      },
      {
        id: 'description',
        label: 'Descrição',
        type: 'textarea',
        description: 'Descrição da conta',
        isrequired: false,
        isdisabled: false,
        ishidden: false,
        min: 0,
        max: 0,
        style: '',
        size: 12,
        actions: ['create', 'update', 'search'],
      },
    ],
  },
];

export const FooterItem: FooterSchemaType = {
  year: 2022,
  description: 'LowCodeSystem',
  version: '1.0.0',
  socialnetworks: [
    { label: 'Facebook', icon: 'bi bi-facebook', url: 'https://www.facebook.com/' },
    { label: 'Twitter', icon: 'bi bi-twitter', url: 'https://twitter.com/' },
    { label: 'Instagram', icon: 'bi bi-instagram', url: 'https://www.instagram.com/' },
    { label: 'Github', icon: 'bi bi-github', url: '' },
  ],
};

export interface ServiceSchemaType {
  name: string;
  description: string;
  url: string;
  apis: {
    name: string;
    description: string;
    path: string;
    method: 'GET' | 'POST' | 'PUT' | 'DELETE';
    headers: Array<{ key: string; value: string }>;
    body: string;
    response: string;
  }[];
}

export const ServicesSchema: Array<ServiceSchemaType> = [
  {
    name: 'Pokemon',
    description: 'Category',
    url: 'https://pokeapi.co/api/v2',
    apis: [
      {
        name: 'getPokemons',
        description: 'Return all pokemons',
        path: '/pokemon/?limit=81',
        method: 'GET',
        headers: [],
        body: '',
        response: '',
      },
    ],
  },
];

export interface ScriptType {
  name: string;
  description: string;
  script: string;
}

export const Scripts: Array<ScriptType> = [
  {
    name: 'Category',
    description: 'Category',
    script: 'alert("teste")',
  },
];
