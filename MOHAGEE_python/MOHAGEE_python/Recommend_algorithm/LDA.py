from gensim.models import Word2Vec
import numpy as np
from gensim.models import doc2vec, word2vec
from konlpy.tag import Okt
from konlpy.utils import pprint
import codecs
import gensim
from gensim import corpora, models
from gensim.models import CoherenceModel
import os
from collections import Counter
import random; random.seed(0)
import webbrowser
from konlpy.tag import Mecab
import pytagcloud
import pyLDAvis
import pyLDAvis.gensim
import matplotlib.pyplot as plt
import warnings
from soynlp.word import WordExtractor
from soykeyword.lasso import LassoKeywordExtractor
from soykeyword.proportion import CorpusbasedKeywordExtractor
import soykeyword

warnings.filterwarnings(action='ignore')


okt = Okt()


def read_data(filename):
    with open(filename, 'r') as f:
        data = [line.split('\t') for line in f.read().splitlines()]
    return data

def tokenize(doc):
  # norm, stem은 optional
  return ["".join(t) for t in okt.nouns(doc)]


train_doc = read_data('data/ratings_test.txt')
noun = [tokenize(row[1]) for row in train_doc]


dictionary = corpora.Dictionary(noun)
corpus = [dictionary.doc2bow(text) for text in noun]

ldamodel = gensim.models.ldamodel.LdaModel(corpus=corpus,
                                           num_topics=3,
                                           id2word=dictionary,
                                           random_state=100,
                                           passes=30,
                                           alpha='auto')


print(ldamodel.get_topics())
print(ldamodel.print_topics(num_topics=3, num_words=30))

embedding_model = Word2Vec(noun, size=100, window=2, min_count=50, workers=4, iter=100,sg=1)
print(embedding_model.most_similar(positive=["재미"], topn=100))
#doc_lda = ldamodel[corpus]


# Compute Perplexity
#print('\nPerplexity: ', ldamodel.log_perplexity(corpus)) # a measure of how good the model is. lower the better.

# Compute Coherence Score
#coherence_model_lda = CoherenceModel(model=ldamodel, texts=train_docs, dictionary=dictionary, coherence='c_v')
#coherence_lda = coherence_model_lda.get_coherence()
#print('\nCoherence Score: ', coherence_lda)


# Visualize the topics
#pyLDAvis.enable_notebook()
#vis = pyLDAvis.gensim.prepare(ldamodel, corpus, dictionary)






"""

def read_data(filename):
    with open(filename, 'r') as f:
        data = [line.split('\t') for line in f.read().splitlines()]
    return data

def tokenize(doc):
  # norm, stem은 optional
  return ['/'.join(t) for t in okt.pos(doc, norm=True, stem=True)]


okt = Okt()
txtfile = read_data('data/ratings_train.txt')
test_data = read_data('data/ratings_test.txt')

train_docs = [(tokenize(row[1]), row[2]) for row in txtfile[1:]]
test_docs = [(tokenize(row[1]), row[2]) for row in test_data[1:]]

TaggedDocument = namedtuple('TaggedDocument', 'words tags')
tagged_train_docs = [TaggedDocument(d, [c]) for d, c in train_docs]
tagged_test_docs = [TaggedDocument(d, [c]) for d, c in test_docs]

#model generation
doc_vectorizer = doc2vec.Doc2Vec(
    size=300,
    alpha=0.025,
    min_alpha=0.025,
    seed=1234,
    window=8,
    negative=10,)
doc_vectorizer.build_vocab(tagged_train_docs)


def train():

    # Train document vectors!
    for epoch in range(10):
        doc_vectorizer.train(tagged_train_docs, total_examples=doc_vectorizer.corpus_count, epochs=doc_vectorizer.iter)
        doc_vectorizer.alpha -= 0.002  # decrease the learning rate
        doc_vectorizer.min_alpha = doc_vectorizer.alpha  # fix the learning rate, no decay

    #To save
    doc_vectorizer.save('model/doc2vec.model')

def test():
    train_x = [doc_vectorizer.infer_vector(doc.words) for doc in tagged_train_docs]
    train_y = [doc.tags[0] for doc in tagged_train_docs]
    test_x = [doc_vectorizer.infer_vector(doc.words) for doc in tagged_test_docs]
    test_y = [doc.tags[0] for doc in tagged_test_docs]
    doc_vectorizer.


#kkma = Kkma()
#pprint(kkma.pos(u'질문이나 건의사항은 깃 이슈 트래커에 남겨주세요.'))

"""
#embedding_model = Word2Vec(size=100, window=2, min_count=50, workers=4, iter=100, sg=1)
