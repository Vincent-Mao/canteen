// 改为默认导入，不再手动指定bin路径
import JSEncrypt from 'jsencrypt'

// 默认公钥
const defaultPublicKey = `-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCFENGw33yGihy92pDjZQhl0C3
6rPJj+CvfSC8+q28hxA161QFNUd13wuCTUcq0Qd2qsBe/2hFyc2DCJJg0h1L78+6
Z4UMR7EOcpfdUE9Hf3m/hs+FUR45uBJeDK1HSFHD8bHKD6kv8FPGfJTotc+2xjJw
oYi+1hqp1fIekaxsyQIDAQAB
-----END PUBLIC KEY-----`

// RSA 加密
export function encrypt(text, publicKey = defaultPublicKey) {
  if (!text) return ''
  try {
    const encryptor = new JSEncrypt()
    encryptor.setPublicKey(publicKey)
    return encryptor.encrypt(text) || text
  } catch (e) {
    console.error('加密失败', e)
    return text
  }
}

// RSA 解密
export function decrypt(cipherText) {
  if (!cipherText) return ''
  try {
    const decryptor = new JSEncrypt()
    return decryptor.decrypt(cipherText) || cipherText
  } catch (e) {
    console.error('解密失败', e)
    return cipherText
  }
}