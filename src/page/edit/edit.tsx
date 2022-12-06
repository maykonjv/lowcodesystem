import { Breadcrumb } from '../../components/Breadcrumb/Breadcrumb';
import SidebarMenu from '../../template/menu';
import { usePage } from '../page.hook';
import { Button, Flex, SimpleGrid } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';

export function EditPage() {
  const { refs, isLoading, pageSchema, setData, data, handleSave } = usePage();
  const navigate = useNavigate();

  return (
    <SidebarMenu>
      <Breadcrumb page={pageSchema?.label || ''} action='Editar' />
      <SimpleGrid columns={{ base: 1, md: 6, lg: 12 }} spacing={3}></SimpleGrid>
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
          colorScheme='green'
          _hover={{
            bg: 'brand2.400',
          }}
          w='xs'
          isLoading={isLoading}
          onClick={() => navigate(`${pageSchema?.path}/create`)}>
          Cancelar
        </Button>
        <Button
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
    </SidebarMenu>
  );
}
