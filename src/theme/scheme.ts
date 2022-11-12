interface MenuItemProps {
    name: string;
    icon: string;
    path: string;
}


interface FormItemProps {
    path: string;
    name: string;
    icon?: string;
    description?: string;
    action?: Array<'create' | 'update' | 'delete' | 'search' | 'view' | 'custom'>;
    fields?: FieldItemProps[];
}

interface FieldItemProps {
    id: string;
    name: string;
    type: string;
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
}

interface FooterProps {
    year: number;
    application: string;
    version: string;
    socialnetworks: Array<{
        name: string;
        icon: string;
        url: string;
    }>;
}

export const MenuItems: Array<MenuItemProps> = [
    { name: 'Home', icon: 'FiHome', path: '/' },
    { name: 'User', icon: 'FiUser', path: '/user' },
    { name: 'Role', icon: 'FiCompass', path: '/role' },

];

export const FormItems: Array<FormItemProps> = [
    {
        name: 'User',
        icon: 'FiUser',
        path: '/user',
        description: 'User Management',
        action: ['create', 'update', 'delete', 'search', 'view'],
        fields: [
            { id: 'name', name: 'Name', type: 'text', description: 'Name', isrequired: true, isdisabled: false, ishidden: false, hint: 'Name', placeholder: 'Name', default: '', min: 0, max: 0, style: '', size: 12 },
            { id: 'email', name: 'Email', type: 'email', description: 'Email', isrequired: true, isdisabled: false, ishidden: false, hint: 'Email', placeholder: 'Email', default: '', min: 0, max: 0, style: '', size: 12 },
            { id: 'password', name: 'Password', type: 'password', description: 'Password', isrequired: true, isdisabled: false, ishidden: false, hint: 'Password', placeholder: 'Password', default: '', min: 0, max: 0, style: '', size: 12 },
        ],
    },
    {
        name: 'Role',
        icon: 'FiUser',
        path: '/role',
        description: 'Role Management',
        action: ['create', 'update', 'delete', 'search', 'view'],
        fields: [
            { id: 'name', name: 'Name', type: 'text', description: 'Name', isrequired: true, isdisabled: false, ishidden: false, hint: 'Name', placeholder: 'Name', default: '', min: 0, max: 0, style: '', size: 12 },
            { id: 'description', name: 'Description', type: 'text', description: 'Description', isrequired: true, isdisabled: false, ishidden: false, hint: 'Description', placeholder: 'Description', default: '', min: 0, max: 0, style: '', size: 12 },
        ],
    },
];

export const FooterItem: FooterProps = {
    year: 2020,
    application: 'React Admin',
    version: '1.0.0',
    socialnetworks: [
        { name: 'Facebook', icon: 'FiFacebook', url: 'https://www.facebook.com/' },
        { name: 'Twitter', icon: 'FiTwitter', url: 'https://twitter.com/' },
        { name: 'Instagram', icon: 'FiInstagram', url: 'https://www.instagram.com/' },
        { name: 'Github', icon: 'FiGithub', url: '' },
    ],
};
