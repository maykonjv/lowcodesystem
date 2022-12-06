import { BreadcrumbItem, BreadcrumbLink, Breadcrumb as BreadcrumbChakra, Box } from '@chakra-ui/react';
import { BiChevronRight } from 'react-icons/bi';

export const Breadcrumb = ({ page, action }: { page: string; action?: string }) => {
  if (!page) return null;
  return (
    <Box display='flex' justifyItems='center' borderWidth={1} p={3} mb={5} borderRadius={'lg'}>
      <BreadcrumbChakra spacing='8px' separator={<BiChevronRight color='gray.500' />}>
        <BreadcrumbItem>
          <BreadcrumbLink>{page}</BreadcrumbLink>
        </BreadcrumbItem>

        {action && (
          <BreadcrumbItem isCurrentPage>
            <BreadcrumbLink href='#'>{action}</BreadcrumbLink>
          </BreadcrumbItem>
        )}
      </BreadcrumbChakra>
    </Box>
  );
};
