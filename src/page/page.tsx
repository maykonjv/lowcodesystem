import { Button, Flex, SimpleGrid } from "@chakra-ui/react"
import { useNavigate } from 'react-router-dom';
import { Input } from "../components/input/input";
import SidebarMenu from "../template/menu"
import { FormItems } from "../theme/scheme";
import { usePage } from "./page.hook";

export const Page = () => {
    const { path, action, form, setForm, isLoading } = usePage()
    const navigate = useNavigate()

    return (
        <SidebarMenu>
            <SimpleGrid columns={{ base: 1, md: 6, lg: 12 }} spacing={3}>
                {FormItems.find((item) => item.path === `/${path}`)?.fields?.map((field, index) => (
                    <Input key={index} id={field.id} error='' name={field.name} description={field.description} type={field.type} value={form[field.id]} onChange={(e: any) => setForm({ ...form, [field.id]: e.target.value })} />
                ))}
            </SimpleGrid>
            <Flex flexDir="row-reverse" mt={10}>
                <Button
                    colorScheme={'green'}
                    _hover={{
                        bg: "brand2.400",
                    }}
                    w={"xs"}
                    isLoading={isLoading}
                    onClick={() => navigate(`/${path}/create`)}
                >
                    Novo
                </Button>
            </Flex>
        </SidebarMenu>

    )
}