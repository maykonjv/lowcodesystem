import React from 'react'
import { useStyletron } from 'baseui'
import Filter from 'baseui/icon/filter'
import { Label2, Paragraph4 } from 'baseui/typography'

import { pages } from '../../assets/constants'
import { PageType } from '../../assets/types'

const Content = ({ pageID }: { pageID: string }) => {
  const [css] = useStyletron()
  const [page, setPage] = React.useState<PageType>({} as PageType)

  React.useEffect(() => {
    setPage(pages.filter(page => page.id === pageID)[0])
  }, [pageID])

  return (
    <div
      className={css({
        width: '100%',
        borderRadius: '0.5rem',
        background: '#fff',
        border: '1px solid #DFE0EB',
        overflow: 'hidden',
        '@media (max-width: 768px)': {
          margin: '0 1.5rem'
        }
      })}
    >
      <div
        className={css({
          padding: '2rem',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between'
        })}
      >
        <Label2>{page?.title}</Label2>
        <div
          className={css({
            display: 'flex',
            alignItems: 'center',
            cursor: 'pointer'
          })}
        >
          <Paragraph4
            className={css({
              display: 'flex',
              alignItems: 'center'
            })}
          >
            <Filter
              size="2rem"
              className={css({
                marginRight: '0.3rem'
              })}
            />
            Filter
          </Paragraph4>
        </div>
      </div>
      {page?.actionNew &&
        page.actionNew?.components?.map(component => (
          <div
            key={component.id}
            className={css({
              padding: '1rem',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'space-between'
            })}
          >
            <Label2>{component.label}</Label2>
          </div>
        ))}
      {/* <Table columns={tableTitles} data={data} /> */}
    </div>
  )
}
export default Content
