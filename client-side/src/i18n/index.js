import { createI18n } from 'vue-i18n'
import en from './locales/en.json'
import vi from './locales/vi.json'

const SUPPORTED = ['en', 'vi']

function normalizeToSupported(lang) {
  if (!lang || typeof lang !== 'string') return null
  const short = lang.toLowerCase().split('-')[0]
  return SUPPORTED.includes(short) ? short : null
}

function resolveInitialLocale() {
  try {
    // 1) Saved preference
    const saved = localStorage.getItem('locale') || localStorage.getItem('lang')
    const savedNorm = normalizeToSupported(saved)
    if (savedNorm) return savedNorm

    // 2) Browser preferences
    const candidates = []
    if (Array.isArray(navigator.languages)) candidates.push(...navigator.languages)
    if (navigator.language) candidates.push(navigator.language)
    // Some browsers expose userLanguage (IE legacy)
    if (navigator.userLanguage) candidates.push(navigator.userLanguage)

    for (const cand of candidates) {
      const norm = normalizeToSupported(cand)
      if (norm) return norm
    }
  } catch (e) {
    // ignore and fallback
  }
  // 3) Fallback
  return 'en'
}

const initialLocale = resolveInitialLocale()

// Persist the resolved locale so the rest of the app (and API headers) can read it
try {
  localStorage.setItem('locale', initialLocale)
} catch {}

const i18n = createI18n({
  legacy: false,
  locale: initialLocale,
  fallbackLocale: 'en',
  messages: {
    en,
    vi
  }
})

export default i18n