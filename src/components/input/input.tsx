import { FormControl, FormErrorMessage, FormHelperText, FormLabel, GridItem, Input as InputChackra } from "@chakra-ui/react"

export const Input = (props: any) => {
    const { id, name, description, type, value, onChange, error, ...rest } = props
    return (
        <GridItem key={`${id}_div`} id={`${id}_div`} colSpan={{ base: 1, md: 2, lg: 3 }}>
            <FormControl id={`${id}_control`} isInvalid={error}>
                <FormLabel id={`${id}_label`}>{name}</FormLabel>
                <InputChackra
                    id={`${id}_input`}
                    type={type}
                    value={value}
                    onChange={onChange}
                    {...rest}
                />
                {!error ? (
                    <FormHelperText id={`${id}_helper`}>
                        {description}
                    </FormHelperText>
                ) : (
                    <FormErrorMessage id={`${id}_error`}>{error}</FormErrorMessage>
                )}
            </FormControl>
        </GridItem>
    )
}
