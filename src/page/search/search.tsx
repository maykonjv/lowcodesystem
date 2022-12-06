import { Breadcrumb } from '../../components/Breadcrumb/Breadcrumb';
import { AlertModal } from '../../components/alert/alert';
import { Component } from '../../components/component';
import { DataTable } from '../../components/datatable/datatable';
import SidebarMenu from '../../template/menu';
import { usePage } from '../page.hook';
import { Button, Flex, SimpleGrid, Spacer } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';

export function SearchPage() {
  const {
    refs,
    modal,
    isOpen,
    onClose,
    isLoading,
    pageSchema,
    setData,
    data,
    tablecolumns,
    handleSearch,
    handleClear,
    handleDelete,
    navigate,
  } = usePage();

  return (
    <SidebarMenu>
      <AlertModal props={modal} isOpen={isOpen} onClose={onClose} />
      <Breadcrumb page={pageSchema?.label || ''} action='Pesquisa' />
      <SimpleGrid columns={{ base: 1, md: 6, lg: 12 }} spacing={3} bg={'white'} borderRadius={10} p={1}>
        {pageSchema?.components?.map((field, index) => {
          if (!field.actions?.includes('search')) return null;
          return <Component key={index} field={field} refs={refs} data={data} setData={setData} />;
        })}
      </SimpleGrid>
      <Flex flexDir='row-reverse' my={10}>
        <Button
          colorScheme='green'
          _hover={{
            bg: 'brand2.400',
          }}
          w='xs'
          isLoading={isLoading}
          onClick={handleSearch}>
          Pesquisar
        </Button>
        <Button
          colorScheme='gray'
          _hover={{
            bg: 'brand2.400',
          }}
          w='md'
          isLoading={isLoading}
          onClick={handleClear}>
          Limpar
        </Button>
        <Spacer />
        <Button
          hidden={!pageSchema?.actions?.includes('create')}
          colorScheme='green'
          _hover={{
            bg: 'brand2.400',
          }}
          w='xs'
          isLoading={isLoading}
          onClick={() => navigate(`${pageSchema?.path}/create`)}>
          Novo
        </Button>
      </Flex>
      <DataTable
        columns={tablecolumns}
        columnsSchema={pageSchema?.tablecolumns}
        data={data.searchResult}
        handleView={(key) => navigate(`${pageSchema?.path}/view/${key}`)}
        handleEdit={(key) => navigate(`${pageSchema?.path}/edit/${key}`)}
        handleDelete={handleDelete}
        actions={pageSchema?.actions}
      />
    </SidebarMenu>
  );
}
