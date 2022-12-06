import { Breadcrumb } from '../../components/Breadcrumb/Breadcrumb';
import SidebarMenu from '../../template/menu';
import { usePage } from '../page.hook';
import { Button, Flex, SimpleGrid } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';

export function ViewPage() {
  const { refs, isLoading, pageSchema, setData, data, handleSave } = usePage();
  const navigate = useNavigate();

  return (
    <SidebarMenu>
      <Breadcrumb page={pageSchema?.label || ''} action='Visualizar' />
      <SimpleGrid columns={{ base: 1, md: 6, lg: 12 }} spacing={3}></SimpleGrid>
      <Flex flexDir='row-reverse' mt={10}>
        <Button w='xs' isLoading={isLoading} onClick={() => navigate(-1)}>
          Voltar
        </Button>
      </Flex>
    </SidebarMenu>
  );
}
