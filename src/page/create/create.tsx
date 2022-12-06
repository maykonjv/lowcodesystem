import { Breadcrumb } from '../../components/Breadcrumb/Breadcrumb';
import { AlertModal } from '../../components/alert/alert';
import { Component } from '../../components/component';
import SidebarMenu from '../../template/menu';
import { usePage } from '../page.hook';
import { Button, Flex, SimpleGrid } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';

export function CreatePage() {
  const { refs, isLoading, pageSchema, setData, data, modal, isOpen, onClose, handleSave } = usePage();
  const navigate = useNavigate();

  return (
    <SidebarMenu>
      <AlertModal props={modal} isOpen={isOpen} onClose={onClose} />
      <Breadcrumb page={pageSchema?.label || ''} action='Cadastro' />
      <SimpleGrid columns={{ base: 1, md: 6, lg: 12 }} spacing={3} bg={'white'} borderRadius={10} p={1}>
        {pageSchema?.components?.map((field, index) => {
          if (!field.actions?.includes('create')) return null;
          return <Component key={index} field={field} refs={refs} data={data} setData={setData} />;
        })}
      </SimpleGrid>
      <Flex flexDir='row-reverse' mt={10}>
        <Button
          colorScheme='green'
          _hover={{
            bg: 'brand2.400',
          }}
          w='xs'
          isLoading={isLoading}
          onClick={handleSave}>
          Gravar
        </Button>
        <Button
          _hover={{
            bg: 'brand2.400',
          }}
          w='xs'
          isLoading={isLoading}
          onClick={() => navigate(-1)}>
          Cancelar
        </Button>
      </Flex>
    </SidebarMenu>
  );
}
