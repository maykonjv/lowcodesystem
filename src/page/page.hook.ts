import { AlertModal, AlertModalType } from '../components/alert/alert';
import { PageSchemaType, PagesSchema } from '../config/schema/schema';
import { api, fetchApi } from '../service/api';
import { useDisclosure } from '@chakra-ui/react';
import { ColumnDef, createColumnHelper } from '@tanstack/react-table';
import { useState, useEffect, useRef } from 'react';
import { Navigate, useNavigate, useParams } from 'react-router-dom';

export const usePage = () => {
  const { path, action } = useParams();
  const [isLoading, setIsLoading] = useState(false);
  const refs = useRef<any>({});
  const [pageSchema, setPageSchema] = useState({} as PageSchemaType);
  const [tablecolumns, setTablecolumns] = useState<ColumnDef<any, any>[]>([]);
  const { onClose, isOpen, onOpen } = useDisclosure();
  const [modal, setModal] = useState<AlertModalType>({
    message: '',
    title: 'Configuração Penalti',
    status: 'info',
  } as AlertModalType);
  const [data, setData] = useState({
    searchResult: [],
  } as any);
  const columnHelper = createColumnHelper<any>();
  const navigate = useNavigate();

  useEffect(() => {
    console.log('usePage', path, action, PagesSchema);
    const _Page = PagesSchema.find((item) => item.path === `/${path}`);
    if (_Page) {
      setPageSchema(_Page);
    } else {
      setPageSchema({} as PageSchemaType);
    }
    const columns = [
      columnHelper.accessor('_key_', {
        cell: (info) => info.getValue(),
      }),
    ];

    _Page?.tablecolumns?.forEach((item) => {
      columns.push(
        columnHelper.accessor(item.id, {
          cell: (info) => info.getValue(),
          header: item.label,
          meta: {
            isNumeric: item.isNumeric,
          },
        })
      );
    });
    setTablecolumns(columns);
  }, [path, action]);

  const handleSave = () => {
    try {
      setIsLoading(true);
      console.log('save', refs, data);
      setModal({
        ...modal,
        message: 'Salvo com sucesso',
        title: PagesSchema.find((item) => item.path === `/${path}`)?.description,
        status: 'success',
        btn: {
          text: 'Voltar',
          onClick: () => {
            onClose();
            navigate(-1);
          },
        },
      });
      onOpen();
    } catch (e) {
      console.log('error', e);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSearch = async () => {
    try {
      setIsLoading(true);
      const resp = await fetchApi('Pokemon', 'getPokemons', {});
      let _data;
      eval('_data = resp.data.results.filter((item) => item.name.includes("aur"));');
      console.log('==>', _data);
      const result = [
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
        { color: 1, description: 'Habitação', active: 'true' },
        { color: 2, description: 'Transporte', active: 'true' },
      ];
      const resultKeys = result.map((item, i) => {
        return { ...item, _key_: String(i) };
      });
      setData({ ...data, searchResult: resultKeys });
    } catch (err) {
      console.log(err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleClear = () => {
    setData({ ...data, searchResult: [] });
  };

  const handleDelete = (_key_: string) => {
    console.log('delete', _key_);
    setModal({
      ...modal,
      message: 'Deseja realmente excluir o registro?',
      title: 'Excluir',
      status: 'info',
      btn: {
        text: 'Excluir',
        onClick: () => {
          console.log('delete', _key_);
          onClose();
        },
      },
    });
    onOpen();
  };

  return {
    path,
    modal,
    isOpen,
    onClose,
    action,
    refs,
    pageSchema,
    isLoading,
    setData,
    data,
    tablecolumns,
    handleSave,
    handleSearch,
    handleClear,
    handleDelete,
    navigate,
  };
};
