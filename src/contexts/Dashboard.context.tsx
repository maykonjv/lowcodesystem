//create context api for pages
// Language: typescript
// Path: src\contexts\Dashboard.context.tsx

import { createContext, useState } from 'react'

type DashboardContextType = {
  page: any
  setPage: (page: any) => void
}

export const DashboardContext = createContext<DashboardContextType>(
  {} as DashboardContextType
)

export function DashboardProvider({ children }: { children: React.ReactNode }) {
  const [page, setPage] = useState('')

  return (
    <DashboardContext.Provider value={{ page, setPage }}>
      {children}
    </DashboardContext.Provider>
  )
}
