import {
  Box,
  Center,
  FormLabel,
  IconButton,
  Image,
  Spinner,
  SystemProps,
  Text,
  Wrap,
  WrapItem,
} from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { FaTrash } from 'react-icons/fa';

function ImagesUpload({
  description,
  _default = [],
  setImages,
  max_count = 1,
  label,
  accept = 'image/png',
  placeholder = '',
  mode = 'cover',
  isDisabled = false,
  hidden = false,
  isLoading,
}: {
  description?: string;
  label: string;
  _default?: any[];
  setImages: (value: any[]) => void;
  max_count?: number;
  accept?: string;
  placeholder?: string;
  mode?: SystemProps['objectFit'];
  isDisabled?: boolean;
  hidden?: boolean;
  isLoading?: boolean;
}) {
  const inputRef = useRef<any>();
  const [uploadedFiles, setUploadedFiles] = useState(_default);
  const [fileLimit, setFileLimit] = useState(false);

  // useEffect(() => {
  //     setImages(uploadedFiles);
  // }, [setImages, uploadedFiles])

  useEffect(() => {
    setUploadedFiles(_default);
  }, [_default]);

  const handleUploadFiles = (files: any) => {
    const uploaded = [...uploadedFiles];
    let limitExceeded = false;
    files.some((file: any) => {
      if (uploaded.findIndex((f: any) => f.name === file.name) === -1) {
        uploaded.push(file);
        if (uploaded.length === max_count) setFileLimit(true);
        if (uploaded.length > max_count) {
          alert(`You can only add a maximum of ${max_count} files`);
          setFileLimit(false);
          limitExceeded = true;
          return true;
        }
      }
      return false;
    });
    if (!limitExceeded) {
      setUploadedFiles(uploaded);
      setImages(uploaded);
    }
  };

  const handleFileEvent = (e: any) => {
    const chosenFiles = Array.prototype.slice.call(e.target.files);
    handleUploadFiles(chosenFiles);
  };

  const deleteFile = (file: any) => {
    setFileLimit(false);
    setUploadedFiles(uploadedFiles.filter((f) => f !== file));
    setImages(uploadedFiles.filter((f) => f !== file));
  };

  return (
    <Box id='bannerUpload' mt={'20px !important'} hidden={hidden}>
      <input
        id='fileUpload'
        type='file'
        multiple
        accept={accept}
        ref={inputRef}
        onChange={handleFileEvent}
        disabled={isDisabled ? isDisabled : fileLimit}
        style={{ display: 'none' }}
      />
      <FormLabel htmlFor='writeUpFile'>{label}</FormLabel>
      <Text fontSize='smaller' color='gray.500' mb={2} mt={-3}>
        {description}
      </Text>
      <Wrap>
        {uploadedFiles.map((file, i) => (
          <WrapItem key={typeof file === 'string' ? file : file.name + i}>
            <div style={{ position: 'relative', width: 300 }}>
              <Image
                borderRadius={'md'}
                bgColor={'white'}
                src={typeof file == 'string' ? file : URL.createObjectURL(file)}
                style={{ width: 300, height: 300 }}
                title={`${uploadedFiles.length}/${max_count}\n${placeholder}`}
                fit={mode}
              />
              {!isDisabled && (
                <IconButton
                  bgColor={'transparent'}
                  onClick={() => deleteFile(file)}
                  aria-label='Excluir banner'
                  style={{ position: 'absolute', right: '0px', top: '0px' }}
                  icon={<FaTrash color='red' />}
                />
              )}
            </div>
          </WrapItem>
        ))}
        {uploadedFiles.length < max_count && (
          <Center w={300} h={300} bg='white' borderRadius={'md'} onClick={() => inputRef.current.click()}>
            <Box>
              <Center color={'gray.400'}>
                <Text>{isLoading ? <Spinner size='sm' /> : 'Clique para adicionar'}</Text>
              </Center>
              <br />
              <Center color={'gray.400'}>
                <Text>{`${uploadedFiles.length}/${max_count}`}</Text>
              </Center>
              <Center color={'gray.400'}>
                <Text>{placeholder}</Text>
              </Center>
            </Box>
          </Center>
        )}
      </Wrap>
    </Box>
  );
}

export default ImagesUpload;
