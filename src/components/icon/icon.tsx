export default function Icon({ type = '', ...props }) {
    const TheIcon = require(`react-icons/fi`)[type];

    return <TheIcon  {...props} />
}

